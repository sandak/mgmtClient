package GuiView;



import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public abstract class ServerDisplayer extends Canvas {
		protected boolean serverStatus;
		
	ServerDisplayer(Composite arg0,int arg1) {
		super(arg0, arg1);
		serverStatus =false;
	}


	public boolean isServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {								//redraws the Widget.
		    	redraw();
		    }
		});
	}

}
