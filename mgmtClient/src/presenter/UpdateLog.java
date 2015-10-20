package presenter;

public class UpdateLog extends CommonCommand {
	/**
	 * Defines what the Command ClientsUpdated should do.
	 * @author Guy Golan & Amit Sandak
	 *
	 */
	public UpdateLog(Presenter presenter) {
		super(presenter);
		
	}
	/**
	 * Activates the matching methods to update the components in the program with the new log using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		presenter.getView().updateLog(param);

	}

}
