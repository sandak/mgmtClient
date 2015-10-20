package presenter;

public class KickRequest extends CommonCommand {
	/**
	 * Defines what the Command KickRequest should do.
	 * @author Guy Golan & Amit Sandak
	 *
	 */
	public KickRequest(Presenter presenter) {
		super(presenter);
	}
	/**
	 * Activates the matching methods of kick client request using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		
		presenter.getModel().kick(param);

	}

}
