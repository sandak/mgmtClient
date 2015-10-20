package presenter;
/**
 * Defines what the Command ShutdownRequest should do.
 * @author Guy Golan & Amit Sandak
 *
 */
public class ShutdownRequest extends CommonCommand {

	public ShutdownRequest(Presenter presenter) {
		super(presenter);
	}
	/**
	 * Activates the matching methods of shutdown request using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		presenter.getModel().shutDownProtocol();

	}

}
