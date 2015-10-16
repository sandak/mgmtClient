package presenter;


public class UpdateStatus extends CommonCommand {

	public UpdateStatus(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String param) {
		System.out.println("dsdsd");
		if (param.equals("online")){
			presenter.getView().setStatus(true);}
		else
			presenter.getView().setStatus(false);

	}

}
