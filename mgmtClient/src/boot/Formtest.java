package boot;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import GuiView.BasicWindow;
import GuiView.Form;
import presenter.Properties;

public class Formtest {

	public static void main(String[] args) {
	
				Form f = new Form(/*new Shell(Display.getCurrent()),*/ Properties.class, "form test", 500, 500);
		

f.run();
Properties p = (Properties) f.getObject();
System.out.println(p.getMgmtPort());
System.out.println(p.getUi());
	}

}
