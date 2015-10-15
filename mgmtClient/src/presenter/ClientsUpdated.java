package presenter;

public class ClientsUpdated extends CommonCommand {

	public ClientsUpdated(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String param) {
		presenter.getView().setClientList(presenter.getModel().getClientsList());

	}

}
