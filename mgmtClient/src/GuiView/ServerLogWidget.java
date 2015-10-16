package GuiView;

import java.sql.Time;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ServerLogWidget extends ServerDisplayer {

	Text log;
	String displayedText;
	Date d ;
	ServerLogWidget(Composite arg0, int arg1) {
		super(arg0, arg1);
		d= new Date();
		log = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL |SWT.MULTI | SWT.READ_ONLY );
		log.setSize(450	, 230);
		
		log.setBackground(new Color(getDisplay(), 255,255,255));
		displayedText = ""+d.toString()+" --->>> Admin Session Started.";
		
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				log.setText(displayedText);
			}
		});
	}

	public Text getLog() {
		return log;
	}

	public void setLog(Text log) {
		this.log = log;
	}

	public String getDisplayedText() {
		return displayedText;
	}

	public void setDisplayedText(String displayedText) {
		this.displayedText = displayedText;
	}
	
	public void appendText(String text)
	{
		
		this.displayedText =""+d.toString()+"--->>>"+text;
	}
	
	
	
}
