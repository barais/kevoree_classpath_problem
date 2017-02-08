package p1;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;
import org.kevoree.ContainerRoot;
import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Input;
import org.kevoree.annotation.KevoreeInject;
import org.kevoree.annotation.Output;
import org.kevoree.annotation.Param;
import org.kevoree.annotation.Start;
import org.kevoree.annotation.Stop;
import org.kevoree.api.KevScriptService;
import org.kevoree.api.ModelService;
import org.kevoree.api.Port;
import org.kevoree.api.handler.UUIDModel;
import org.kevoree.api.handler.UpdateCallback;
import org.kevoree.factory.DefaultKevoreeFactory;
import org.kevoree.factory.KevoreeFactory;
import org.kevoree.pmodeling.api.ModelCloner;

@ComponentType(version = 1, description = "")
@SuppressWarnings("all")
public class Test1 {
  @Start
  public String startComponent() {
    return InputOutput.<String>println("Start");
  }
  
  @Stop
  public String stopComponent() {
    return InputOutput.<String>println("Stop");
  }
  
  @Input
  public String consumeHello(final Object o) {
    String _xblockexpression = null;
    {
      String _string = o.toString();
      String _plus = ("Received " + _string);
      InputOutput.<String>println(_plus);
      String _xifexpression = null;
      if ((o instanceof String)) {
        String _xblockexpression_1 = null;
        {
          String msg = ((String) o);
          _xblockexpression_1 = InputOutput.<String>println(("HelloConsumer received: " + msg));
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Output
  private Port simplePort;
  
  @Param(defaultValue = "2000")
  @Accessors
  private int myparameter = 2000;
  
  private KevoreeFactory fact = new DefaultKevoreeFactory();
  
  @KevoreeInject
  private KevScriptService kevScriptService;
  
  @KevoreeInject
  private ModelService modelService;
  
  public void adaptComponent() {
    try {
      UUIDModel model = this.modelService.getCurrentModel();
      ModelCloner _createModelCloner = this.fact.createModelCloner();
      ContainerRoot _model = model.getModel();
      ContainerRoot _clone = _createModelCloner.<ContainerRoot>clone(_model);
      ContainerRoot localModel = ((ContainerRoot) _clone);
      this.kevScriptService.execute("//kevscripttoapply", localModel);
      final UpdateCallback _function = new UpdateCallback() {
        public void run(final Boolean e) {
          InputOutput.<String>println("ok");
        }
      };
      this.modelService.update(localModel, _function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Pure
  public int getMyparameter() {
    return this.myparameter;
  }
  
  public void setMyparameter(final int myparameter) {
    this.myparameter = myparameter;
  }
}
