namespace NamedPredicates
{
    public class PredicateSamples
    {
        // Predicate variable possible but not reusable for different 
        // input values. In this case "1".
        private readonly Predicate<Hero> memberPredicate = (hero) => hero.Id == 1;

        // Helper methods that returns a predicate with the given value.
        public static Predicate<Hero> IdMethod(int id)
        {
            return (hero) => hero.Id == id;
        }
        // aka Predicate<Person> IdMethod(int id) => (hero) => hero.Id == id;
        public static Predicate<Hero> NameMethod(string name)
        {
            return (hero) => hero.Name == name;
        }
        // aka Predicate<Person> NameMethod(string name) => (hero) => hero.Name == name;


        public void Execute()
        {
            Console.WriteLine("----- Execute Predicate samples -----");

            // Use an old-school anonymous method
            HeroRepository.PrintBy(delegate(Hero hero) { return hero.Id == 4; });

            // Use a well-known inline lambda
            HeroRepository.PrintBy((hero) => hero.Id == 4);

            // Use a local lambda variable
            Predicate<Hero> idPredicate = (hero) => hero.Id == 4;
            HeroRepository.PrintBy(idPredicate);

            // Use the member variable
            HeroRepository.PrintBy(memberPredicate);

            // Use the method that can be reused with different inputs
            HeroRepository.PrintBy(IdMethod(2));
            HeroRepository.PrintBy(IdMethod(3));

            Console.WriteLine("----- Finish Predicate samples -----");
        }
    }
}
