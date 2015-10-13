package presenter;

public class ChangeServerStatus extends CommonCommand {

	public ChangeServerStatus(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String param) {
		presenter.getModel().startStopServer();

	}

}
