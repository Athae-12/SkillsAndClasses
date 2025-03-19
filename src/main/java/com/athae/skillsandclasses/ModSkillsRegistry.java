import com.athae.skillsandclasses.Spells.FireballSkill;
import com.athae.skillsandclasses.Spells.Skill;

public class ModSkills {
       public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(ForgeRegistries.CUSTOM_REGISTRY, Skillsandclasses.MODID);
       
       public static final RegistryObject<Skill> FIREBALL = SKILLS.register("fireball", () -> new FireballSkill());
   }