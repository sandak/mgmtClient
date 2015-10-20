package presenter;
/**
 * Defines what the Command ClientsUpdated should do.
 * @author Guy Golan & Amit Sandak
 *
 */
public class ClientsUpdated extends CommonCommand {

	public ClientsUpdated(Presenter presenter) {
		super(presenter);
	}
	/**
	 * Activates the matching methods of clients update request using the presenter.
	 */
	@Override
	public void doCommand(String param) {
		presenter.getView().setClientList(presenter.getModel().getClientsList());

	}

}
