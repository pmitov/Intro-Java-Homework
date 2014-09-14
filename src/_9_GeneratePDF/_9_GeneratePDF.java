package _9_GeneratePDF;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class _9_GeneratePDF {

	public static void main(String[] args) throws IOException,
			DocumentException {
		String[] face = new String[] { "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "J", "Q", "K", "A" };
		char[] suit = new char[] { '\u2660', '\u2665', '\u2666', '\u2663' };

		Document document = new Document(PageSize.getRectangle("A4"));

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("deck-of-cards.pdf"));
		document.open();

		BaseFont base = BaseFont.createFont("FreeMono.ttf",
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		PdfContentByte canvas = writer.getDirectContent();

		canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);
		canvas.setRGBColorStroke(0x00, 0x00, 0x00);
		canvas.setLineWidth(1);

		int cardWidth = 80;
		int cardHeight = 120;

		for (int i = 0; i < suit.length; i++) {
			int cardLLX = (595 - cardWidth) / 2;
			int cardLLY = 660 - i * 180;
			double f = Math.sqrt((cardLLX + cardWidth / 2)
					* (cardLLX + cardWidth / 2) + cardLLY * cardLLY);
			double gamma = Math.asin(cardLLY / f);
			for (int j = 0; j < face.length; j++) {

				canvas.saveState();

				double beta = ((face.length - 1) / 2 + 0.05 - j) * Math.PI
						/ face.length;
				// w/o 0.05 the mid card becomes exactly vertical and the border
				// seems much sharper than other cards' borders

				float cosinus = (float) Math.cos(beta);
				float sinus = (float) Math.sin(beta);

				float offsetX = (float) (cardLLX + cardWidth / 2 - f
						* Math.cos(gamma + beta));
				float offsetY = (float) (cardLLY - f * Math.sin(gamma + beta));

				canvas.concatCTM(cosinus, sinus, -sinus, cosinus, offsetX,
						offsetY);

				canvas.roundRectangle(cardLLX, cardLLY, cardWidth, cardHeight, 5);
				canvas.fillStroke();
				canvas.restoreState();

				canvas.saveState();
				canvas.beginText();
				canvas.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
				canvas.setRGBColorStroke(0x00, 0x00, 0x00);
				canvas.setRGBColorFill(0x00, 0x00, 0x00);
				if (i == 1 || i == 2) {
					canvas.setRGBColorStroke(0xFF, 0x00, 0x00);
					canvas.setRGBColorFill(0xFF, 0x00, 0x00);
				}

				// print the face
				canvas.setLineWidth(1.0f);
				canvas.setFontAndSize(base, 15);
				canvas.concatCTM(cosinus, sinus, -sinus, cosinus, offsetX,
						offsetY);
				canvas.setTextMatrix(cardLLX + 6, cardLLY + cardHeight - 16);
				canvas.showText(face[j]);
				if (j == 12) {
					canvas.setTextMatrix(-1, 0, 0, -1, cardLLX + cardWidth - 6,
							cardLLY + 16);
					canvas.showText(face[j]);
				}

				// print the suit
				canvas.setLineWidth(0.0f);
				canvas.setFontAndSize(base, 12);
				canvas.setTextMatrix(cardLLX + 7, cardLLY + cardHeight - 28);
				canvas.showText("" + suit[i]);

				// print the center suit symbol
				if (j == 12) {
					canvas.setTextMatrix(-1, 0, 0, -1, cardLLX + cardWidth - 7,
							cardLLY + 28);
					canvas.showText("" + suit[i]);
					canvas.setFontAndSize(base, 33);
					canvas.setTextMatrix(cardLLX + cardWidth / 2 - 10, cardLLY
							+ cardHeight / 2 - 10);
					canvas.showText("" + suit[i]);
				}

				canvas.endText();
				canvas.restoreState();

			}
		}
		document.close();
	}
}