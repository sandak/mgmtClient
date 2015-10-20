package GuiView;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * The Class ServerLogWidget parse and shows the server logs.
 * 
 *  @author Guy Golan && Amit Sandak.
 */
public class ServerLogWidget extends ServerDisplayer {

	/** The log data. */
	Text log;
	
	/** The displayed log text. */
	String displayedText;
	
	/** The date to attach to the log view. */
	Date d ;
	
	/**
	 * Instantiates a new server log widget.
	 *
	 * @param parent the parent composite
	 * @param param the SWT style parameters
	 */
	ServerLogWidget(Composite parent, int param) {
		super(parent, param);
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

	/**
	 * Gets the log.
	 *
	 * @return the log
	 */
	public Text getLog() {
		return log;
	}

	/**
	 * Sets the log.
	 *
	 * @param log the new log
	 */
	public void setLog(Text log) {
		this.log = log;
	}

	/**
	 * Gets the displayed text.
	 *
	 * @return the displayed text
	 */
	public String getDisplayedText() {
		return displayedText;
	}

	/**
	 * Sets the displayed text.
	 *
	 * @param displayedText the new displayed text
	 */
	public void setDisplayedText(String displayedText) {
		this.displayedText = displayedText;
	}
	
	/**
	 * the new log text to append in the view with the previous text.
	 *
	 * @param text the text new text to append
	 */
	public void appendText(String text)
	{
		this.d = new Date();
		this.displayedText =displayedText+"\n"+d.toString()+" --->>>"+text;
	}
	
	
	
}
