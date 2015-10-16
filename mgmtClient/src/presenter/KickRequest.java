package presenter;

public class KickRequest extends CommonCommand {

	public KickRequest(Presenter presenter) {
		super(presenter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doCommand(String param) {
		
		presenter.getModel().kick(param);

	}

}
