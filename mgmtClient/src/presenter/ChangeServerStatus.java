package presenter;
/**
 * Defines what the Command ChangeServerStatus should do.
 * @author Guy Golan & Amit Sandak
 *
 */
public class ChangeServerStatus extends CommonCommand {

	public ChangeServerStatus(Presenter presenter) {
		super(presenter);
	}
	/**
	 * Activates a chain of start or stop service request using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		presenter.getModel().startStopServer();

	}

}
