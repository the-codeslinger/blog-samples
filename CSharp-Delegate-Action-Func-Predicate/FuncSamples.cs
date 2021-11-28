using System.Xml.Linq;

namespace NamedPredicates
{
    public class FuncSamples
    {
        // Similar to "IdMethod" and "NameMethod" in PredicateSamples
        static readonly Func<int, Predicate<Hero>> IdFuncPredicate = 
                (id) => (hero) => hero.Id == id;
        static readonly Func<string, Predicate<Hero>> NameFuncPredicate = 
                (name) => (hero) => hero.Name == name;

        // Or as a method. But at that point "IdMethod" and "NameMethod"
        // in PredicateSamples are the much better options.
        static Func<int, Predicate<Hero>> IdFuncMethod(int id)
        {
            return (id) => (hero) => hero.Id == id;
        }

        // Sample taking a Func, executing it and returning the result.
        // This is outside the realm of defining a "Named Predicate".
        static void GetAndPrintSupergirl(Func<string, Hero> func)
        {
            var supergirl = func("Supergirl");
            Console.WriteLine(supergirl);
        }

        public static Hero FuncMethod(string name)
        {
            return HeroRepository.GetBy(PredicateSamples.NameMethod(name));
        }

        public void Execute()
        {
            Console.WriteLine("----- Execute Func samples -----");

            // Try to emulate Predicate with a Func<T, R>
            // Predicate takes one parameter and always returns bool.
            Func<Hero, bool> nameFunc = (hero) => hero.Name == "Iron Man";

            // Error: Does not get Person implicitly like Predicate
            //        Requires explicit Person first parameter
            //HeroRepository.GetBy(nameFunc);

            HeroRepository.PrintBy(IdFuncPredicate(5));
            HeroRepository.PrintBy(NameFuncPredicate("Batman"));

            // Use an old-school anonymous method
            GetAndPrintSupergirl(delegate(string name) 
            {
                return HeroRepository.GetBy(PredicateSamples.NameMethod(name));
            });

            // Use func as a regular parameter. Curly braces are not required;
            // only used for making a new line for readability.
            GetAndPrintSupergirl((name) =>
            {
                return HeroRepository.GetBy(PredicateSamples.NameMethod(name));
            });

            // Use an existing method as Action. It matches the required
            // signature.
            GetAndPrintSupergirl(FuncMethod);

            Console.WriteLine("----- Finish Func samples -----");
        }
    }
}
