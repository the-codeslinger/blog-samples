namespace NamedPredicates
{
    class HeroRepository
    {
        private static readonly List<Hero> heroes = new List<Hero>()
        {
            new Hero(1, "Supergirl"),
            new Hero(2, "Batman"),
            new Hero(3, "Iron Man"),
            new Hero(4, "Captain America"),
            new Hero(5, "Black Widow"),
            new Hero(6, "Superman"),
            new Hero(7, "Spiderman")
        };

        /// <summary>
        /// Sample query method that finds an item given a predicate.
        /// 
        /// In this case "Get" means "WriteLine" for simplicity.
        /// </summary>
        public static void PrintBy(Predicate<Hero> predicate)
        {
            Console.WriteLine(heroes.Find(predicate));
        }

        /// <summary>
        /// Sample query method that finds an item given a predicate.
        /// </summary>
        public static Hero GetBy(Predicate<Hero> predicate)
        {
            return heroes.Find(predicate)!;
        }
    }
}
