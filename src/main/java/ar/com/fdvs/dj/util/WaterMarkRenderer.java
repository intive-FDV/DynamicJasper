package ar.com.fdvs.dj.util;

import net.sf.jasperreports.engine.JRAbstractRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.type.ImageTypeEnum;
import net.sf.jasperreports.engine.type.RenderableTypeEnum;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by dj on 01/12/15.
 * Took from: http://stackoverflow.com/questions/11665663/watermark-across-the-page-in-jasperreports
 */
@SuppressWarnings("deprecation")
public class WaterMarkRenderer extends JRAbstractRenderer {
    private boolean m_licenseTrial = false;

    public WaterMarkRenderer(boolean isLicenseTrial)
    {
        m_licenseTrial = isLicenseTrial;
    }

    @Override
    public byte getType()
    {
        // no idea what this does
        return RenderableTypeEnum.SVG.getValue();
    }

    @Override
    public byte getImageType()
    {
        // no idea what this does
        return ImageTypeEnum.UNKNOWN.getValue();
    }

    @Override
    public Dimension2D getDimension() throws JRException
    {
        // A4 in pixel: 595x842
        // this seems to override whatever is configured in jasperreports studio
        return new Dimension(595 - 2 * 40, 700);
    }

    @Override
    public byte[] getImageData() throws JRException
    {
        // no idea what this does
        return new byte[0];
    }

    @Override
    public void render(Graphics2D g2, Rectangle2D rectangle) throws JRException
    {
        if(m_licenseTrial)
        {
            AffineTransform originalTransform = g2.getTransform();

            // just for debugging
            g2.setColor(Color.BLUE);
            g2.draw(rectangle);

            g2.translate(rectangle.getX() + 100, rectangle.getMaxY());
            g2.rotate(-55 * Math.PI / 180);

            Font font = new Font("Arial", Font.PLAIN, 120);
            Shape shape = font.createGlyphVector(g2.getFontRenderContext(), "Trial License").getOutline();
            g2.setColor(new Color(255, 0, 0, 100));
            g2.setStroke(new BasicStroke(1));
            g2.draw(shape);

            g2.setTransform(originalTransform);
        }
    }
}
