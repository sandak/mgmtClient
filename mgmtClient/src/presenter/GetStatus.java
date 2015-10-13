package presenter;

public class GetStatus extends CommonCommand {

	public GetStatus(Presenter presenter) {
		super(presenter);

	}

	@Override
	public void doCommand(String param) {
		System.out.println("hhgh");
		presenter.getModel().getStatus();

	}

}
