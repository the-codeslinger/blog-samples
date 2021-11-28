namespace NamedPredicates
{
    public class ActionSamples
    {
        // Simulate a complex operation where something shall
        // be done with the result.
        static void ExecuteWithHero(string heroName, Action<Hero> action)
        {
            var hero = HeroRepository.GetBy(PredicateSamples.NameMethod(heroName));
            action(hero);
        }

        static void ActionMethod(Hero hero)
        {
            Console.WriteLine("ActionMethod: " + hero);
        }

        public void Execute()
        {
            Console.WriteLine("----- Execute Action samples -----");

            // Use an old-school anonymous method
            ExecuteWithHero("Black Widow", delegate (Hero hero) 
            {
                Console.WriteLine("ActionDelegate: " + hero);
            });

            // Use a well-known inline lambda; curlies only for new line and
            // readability.
            ExecuteWithHero("Black Widow", (hero) =>
            {
                Console.WriteLine("ActionLambda: " + hero);
            });

            // Use an existing method as Action. It matches the required
            // signature.
            ExecuteWithHero("Black Widow", ActionMethod);

            Console.WriteLine("----- Finish Action samples -----");
        }
    }
}
