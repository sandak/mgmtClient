package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import GuiView.Form;
import GuiView.MyObservableGuiView;
import cliView.MyObservableCLIView;
import model.MyObservableModel;
import model.UpdateHandler;
import presenter.Presenter;
import presenter.Properties;
import view.ObservableCommonView;


/**
 * The Class RunMgmtApp run the management application.
 * 
 * @author Guy Golan && Amit Sandak.
 */
public class RunMgmtApp {

	public static void main(String[] args) {
		Form f = new Form(Properties.class,"properties");
		f.run();
		Properties prop=(Properties)f.getObject();

		ObservableCommonView view = null;
		switch (prop.getUi())				//according to the properties initializing the view.
		{
			case "Command line":
				view = new MyObservableCLIView(new BufferedReader(new InputStreamReader(System.in)) , new PrintWriter(System.out));
				break;
			case "Graphic user interface":
				view = new MyObservableGuiView("Server Manager", 800, 500);
				break;
			default:
				view = new MyObservableGuiView("Server Manager", 800, 500);	
		}
				
		MyObservableModel model = new MyObservableModel(new UpdateHandler());
		Presenter p = new Presenter(model,view);		
				
		p.setProperties(prop);		//setting the properties of the system for the presenter.
		p.setDebugMode(true);		//debug mode turned on.
				
		model.addObserver(p);		//presenter observing the model for changes.
		view.addObserver(p);		//presenter observing the view for changes.
				
		p.start();				//start of the user interface.
	}

}