package presenter;
/**
 * Defines what the Command GetStatus should do.
 * @author Guy Golan & Amit Sandak
 *
 */
public class GetStatus extends CommonCommand {

	public GetStatus(Presenter presenter) {
		super(presenter);

	}
	/**
	 * Activates the matching methods of get status request using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		presenter.getModel().getStatus();

	}

}
