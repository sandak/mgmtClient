package presenter;

public class GetData extends CommonCommand {

	public GetData(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String param) {
		presenter.getModel().getData();

	}

}
