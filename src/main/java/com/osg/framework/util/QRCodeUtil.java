package com.osg.framework.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

public class QRCodeUtil {

	/**
	 * 解码二维码
	 * 
	 * @param imageFile
	 *            要解码的二维码图片文件
	 * @return 解码出的二维码信息
	 * @throws IOException
	 */
	public static String decodeQRCode(File imageFile) throws IOException {
		QRCodeDecoder decoder = new QRCodeDecoder();
		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFile);
			String decodedData = new String(decoder.decode(new J2SEImage(image)), "GBK");

			return decodedData;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * 二维码编码 level 1可以支持61个汉字183个字节 level 2可以支持333个汉字999个字节
	 * 
	 * @param text
	 *            要编入二维码的文本内容
	 * @param target
	 *            二维码生成后要存放的文件
	 * @param level
	 *            编码级别，级别越高存储的信息也越多
	 * @throws IOException
	 */
	public static void encodeQRCode(String text, File target, int level, File logo) throws IOException {
		try {
			encodeQRCode(text, new FileOutputStream(target), level, new FileInputStream(logo));
		} catch (FileNotFoundException e) {
			// ignore it
		}
	}

	/**
	 * 二维码编码 level 1可以支持61个汉字183个字节 level 2可以支持333个汉字999个字节
	 * 
	 * @param text
	 *            要编入二维码的文本内容
	 * @param os
	 *            二维码生成后要输出的流
	 * @param level
	 *            编码级别，级别越高存储的信息也越多
	 * @param logo
	 *            要生成中间logo的图片输入流
	 * @throws IOException
	 */
	public static void encodeQRCode(String text, OutputStream os, int level, InputStream logo) throws IOException {
		try {
			int version = 0;
			int limit = 0;
			int size = 0;
			int logoSize = 0;
			if (level == 1) { // 45
				version = 7;
				limit = 183;
				size = 67 + 12 * (version - 1);
				logoSize = 39;
			} else if (level == 2) { // 65
				version = 12;
				limit = 320;
				size = 67 + 12 * (version - 1);
				logoSize = 55;
			} else if (level == 3) { // 97
				version = 20;
				limit = 999;
				size = 67 + 12 * (version - 1);
				logoSize = 75;
			} else
				throw new IOException("不支持的level：" + level);

			Qrcode qrcode = new Qrcode();
			/*** 表示的字符串长度： 容错率(ECC) 显示编码模式(EncodeMode)及版本(Version)有关 ***/
			/*
			 * 二维码的纠错级别(排错率)，共有四级：可选L(7%)、M(15%)、Q(25%)、H(30%)(最高H)。
			 * 纠错信息同样存储在二维码中，纠错级别越高，纠错信息占用的空间越多，那么能存储的有用信息就越少,对二维码清晰度的要求越小
			 */
			qrcode.setQrcodeErrorCorrect('M');
			// 编码模式：Numeric 数字, Alphanumeric 英文字母,Binary 二进制,Kanji 汉字(第一个大写字母表示)
			qrcode.setQrcodeEncodeMode('B');
			/*
			 * 二维码的版本号：也象征着二维码的信息容量；二维码可以看成一个黑白方格矩阵，版本不同，矩阵长宽方向方格的总数量分别不同。
			 * 1-40总共40个版本，版本1为21*21矩阵，版本每增1，二维码的两个边长都增4； 版本2
			 * 为25x25模块，最高版本为是40，是177*177的矩阵；
			 */
			qrcode.setQrcodeVersion(version);

			byte[] d = text.getBytes("utf-8");// text.getBytes("GBK");

			BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

			// createGraphics
			Graphics2D g = bi.createGraphics();

			// set background
			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, size, size);

			g.setColor(Color.BLACK);

			if (d.length > 0 && d.length < limit) {
				boolean[][] b = qrcode.calQrcode(d);
				for (int i = 0; i < b.length; i++) {
					for (int j = 0; j < b.length; j++) {
						if (b[j][i]) {
							g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
						}
					}
				}
			} else
				throw new IOException("要编入二维码的信息过长，限制字节数：" + limit);

			if (logo != null) {
				Image img = ImageIO.read(logo);

				int xy = (size - logoSize) / 2;
				g.drawImage(img, xy, xy, logoSize, logoSize, null);
			}

			g.dispose();
			bi.flush();

			ImageIO.write(bi, "png", os);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
}

class J2SEImage implements QRCodeImage {
	BufferedImage image;

	public J2SEImage(BufferedImage image) {
		this.image = image;
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public int getPixel(int x, int y) {
		return image.getRGB(x, y);
	}

}