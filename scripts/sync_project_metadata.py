#!/usr/bin/env python3
"""Sync project version (from pom.xml) and the JR compatibility table
(from docs/_data/jr_compatibility.yml) into docs/_data/project.yml and
the marker blocks in README.md.
"""
import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

try:
    import yaml
except ImportError:
    yaml = None

POM_NS = {"m": "http://maven.apache.org/POM/4.0.0"}


def get_project_version(pom_path: Path) -> str:
    root = ET.parse(pom_path).getroot()
    # Namespaced pom (normal case)
    version = root.find("m:version", POM_NS)
    if version is None:
        # Fallback: no namespace declared
        version = root.find("version")
    if version is None or not version.text:
        raise RuntimeError(f"Could not find <project><version> in {pom_path}")
    return version.text.strip()


def load_compat_rows(yml_path: Path):
    if yaml is not None:
        with open(yml_path) as f:
            return yaml.safe_load(f) or []
    # Minimal fallback parser if PyYAML isn't available (simple "- dj: x" / "  jr: y" shape only)
    rows = []
    current = {}
    for line in yml_path.read_text().splitlines():
        line = line.strip()
        if not line or line.startswith("#"):
            continue
        if line.startswith("- dj:"):
            if current:
                rows.append(current)
            current = {"dj": line.split(":", 1)[1].strip().strip('"')}
        elif line.startswith("jr:"):
            current["jr"] = line.split(":", 1)[1].strip().strip('"')
    if current:
        rows.append(current)
    return rows


def render_markdown_table(rows) -> str:
    lines = ["| DJ version | JasperReports compatible |", "|---|---|"]
    for row in rows:
        lines.append(f"| {row['dj']} | {row['jr']} |")
    return "\n".join(lines)


def replace_between_markers(
    text: str, start: str, end: str, replacement: str, inline: bool = False
) -> str:
    pattern = re.compile(re.escape(start) + r".*?" + re.escape(end), re.DOTALL)
    if not pattern.search(text):
        raise RuntimeError(f"Markers {start!r} / {end!r} not found")
    wrapped = f"{start}{replacement}{end}" if inline else f"{start}\n{replacement}\n{end}"
    return pattern.sub(lambda _: wrapped, text)


def main():
    repo_root = Path(__file__).resolve().parent.parent
    pom_path = repo_root / "pom.xml"
    compat_path = repo_root / "docs/_data/jr_compatibility.yml"
    project_data_path = repo_root / "docs/_data/project.yml"
    readme_path = repo_root / "README.md"

    version = get_project_version(pom_path)
    project_data_path.write_text(f'version: "{version}"\n')
    print(f"Wrote {project_data_path} (version={version})")

    rows = load_compat_rows(compat_path)
    table_md = render_markdown_table(rows)

    readme = readme_path.read_text()
    readme = replace_between_markers(
        readme, "<!-- dj-version:start -->", "<!-- dj-version:end -->", version, inline=True
    )
    readme = replace_between_markers(
        readme, "<!-- jr-compat:start -->", "<!-- jr-compat:end -->", table_md
    )
    readme_path.write_text(readme)
    print(f"Updated {readme_path} (version={version}, {len(rows)} compatibility rows)")


if __name__ == "__main__":
    sys.exit(main())
