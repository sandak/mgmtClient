package presenter;

public class Message extends CommonCommand {

	public Message(Presenter presenter) {
		super(presenter);

	}

	@Override
	public void doCommand(String param) {
		presenter.getView().display(param);

	}

}
