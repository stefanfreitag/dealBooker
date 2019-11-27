package de.freitag.stefan.alexa.dealbooker;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

@SuppressWarnings("unused")
public final class DealBookerSkillStreamHandler extends SkillStreamHandler {

  private static Skill getSkill() {
    return Skills.standard()
        .addRequestHandlers(
            new LaunchRequestHandler(),
            new HelpIntentHandler(),
            new CancelIntentHandler(),
            new StopIntentHandler(),
            new DealIntentHandler(),
            new SessionEndedRequestHandler())
        .addExceptionHandler(new GenericExceptionHandler())
        .withSkillId("amzn1.ask.skill.aeb31ea6-f0af-49a1-8e4c-9183d077064a")
        .build();
  }

  public DealBookerSkillStreamHandler() {
    super(getSkill());
  }
}
