package presenter;


/**
 * Usually notified from the model when a regular command was completed or not,
 * this command activates and tells the presenter what to do.
 * @author Guy Golan & Amit Sandak.
 *
 */
public class CompletedTask extends CommonCommand {

	public CompletedTask(Presenter presenter) {
		super(presenter);

	}

	@Override
	public void doCommand(String param) {
		
	
	}
}
