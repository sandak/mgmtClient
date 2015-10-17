package presenter;

public class ShutdownRequest extends CommonCommand {

	public ShutdownRequest(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String param) {
		presenter.getModel().shutDownProtocol();

	}

}
