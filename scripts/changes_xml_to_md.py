#!/usr/bin/env python3
"""Regenerate docs/changes.md from src/changes/changes.xml (Maven changes-plugin format)."""
import xml.etree.ElementTree as ET
import re
import sys
from pathlib import Path

TYPE_LABELS = {
    "add": "Add",
    "fix": "Fix",
    "update": "Update",
    "change": "Change",
    "remove": "Remove",
}


def clean(text):
    if text is None:
        return ""
    return re.sub(r"\s+", " ", text.strip())


def main():
    repo_root = Path(__file__).resolve().parent.parent
    src = repo_root / "src/changes/changes.xml"
    dst = repo_root / "docs/changes.md"

    root = ET.parse(src).getroot()
    body = root.find("body")

    lines = [
        "---",
        "layout: page",
        "title: Changes",
        "permalink: /changes/",
        "---",
        "",
        "{% include nav.html %}",
        "",
        "# Changes",
        "",
        "Full version history of DynamicJasper. Source: "
        "[`src/changes/changes.xml`](https://github.com/intive-FDV/DynamicJasper/blob/master/src/changes/changes.xml).",
        "",
    ]

    for release in body.findall("release"):
        version = release.get("version")
        date = release.get("date")
        desc = release.get("desc") or ""
        lines.append(f"## {version} ({date})")
        if desc.strip():
            lines.append(f"*{clean(desc)}*")
        lines.append("")
        for action in release.findall("action"):
            dev = action.get("dev") or ""
            atype = (action.get("type") or "").strip().lower()
            label = TYPE_LABELS.get(atype, atype.capitalize() if atype else "Note")
            text = clean("".join(action.itertext()))
            entry = f"- **{label}**: {text}"
            if dev:
                entry += f" ({dev})"
            lines.append(entry)
        lines.append("")

    dst.write_text("\n".join(lines).rstrip() + "\n")
    print(f"Wrote {dst} with {len(body.findall('release'))} releases")


if __name__ == "__main__":
    sys.exit(main())
