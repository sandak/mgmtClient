package presenter;

public class UpdateLog extends CommonCommand {

	public UpdateLog(Presenter presenter) {
		super(presenter);
		
	}

	@Override
	public void doCommand(String param) {
		presenter.getView().updateLog(param);

	}

}
