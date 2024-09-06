package io.github.nitiaonstudio.ding.data.resources;

import io.github.nitiaonstudio.ding.data.XyWh;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import javax.imageio.IIOException;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

import static java.awt.AlphaComposite.Clear;
import static javax.imageio.ImageIO.createImageInputStream;
import static javax.imageio.ImageIO.read;


@Accessors(fluent = true, makeFinal = true)
@Getter
@Setter
public class BIMG extends BufferedImage {

    @Delegate
    Graphics2D graphics;

    public BIMG(int width, int height, int type) {
        super(width, height, type);
        create()
                .composite(Clear)
                .close()
                .create();

    }

    public BIMG(BufferedImage img) {
        super(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int rgb = img.getRGB(i, j);
                setRGB(i, j, rgb);
            }
        }
    }

    @Override
    public Graphics2D createGraphics() {
        return graphics(super.createGraphics()).graphics;
    }


    public BIMG create() {
        createGraphics();
        return this;
    }

    public BIMG composite(Composite composite) {
        setComposite(composite);
        return this;
    }

    public BIMG close() {
        dispose();
        return this;
    }

    public BIMG rec(int x, int y, int width, int height) {
        fillRect(x, y, width, height);
        return this;
    }


    public BIMG rec(XyWh xywh) {
        return rec(xywh.x, xywh.y, xywh.w, xywh.h);
    }

    public BIMG color(Color color) {
        setColor(color);
        return this;
    }

    public BIMG insertImage(BIMG bimg, int x, int y) {
        drawImage(bimg, x, y, bimg.getWidth(), bimg.getHeight(), (img, infoflags, x1, y1, width, height) -> false);
        return this;
    }
    public BIMG insertImage(BufferedImage bimg, int x, int y) {
        drawImage(bimg, x, y, bimg.getWidth(), bimg.getHeight(), (img, infoflags, x1, y1, width, height) -> false);
        return this;
    }




}
