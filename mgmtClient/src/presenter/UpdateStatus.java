package presenter;

/**
 * Defines what the Command UpdateStatus should do.
 * @author Guy Golan & Amit Sandak
 *
 */
public class UpdateStatus extends CommonCommand {

	public UpdateStatus(Presenter presenter) {
		super(presenter);
	}
	/**
	 * Activates the matching methods to update the program components in  the new service status using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		if (param.equals("online")){
			presenter.getView().setStatus(true);}
		else
			presenter.getView().setStatus(false);

	}

}
