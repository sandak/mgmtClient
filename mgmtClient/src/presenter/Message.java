package presenter;

public class Message extends CommonCommand {

	public Message(Presenter presenter) {
		super(presenter);

	}

	@Override
	public void doCommand(String param) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				presenter.getView().display(param);
				
			}
		}).start();
		

	}

}
