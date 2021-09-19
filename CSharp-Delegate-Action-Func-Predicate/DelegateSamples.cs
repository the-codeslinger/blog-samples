using System.Xml.Linq;

namespace NamedPredicates
{
    public class DelegateSamples
    {
        public delegate Hero CallAHeroByName(string name);

        public void Execute()
        {
            Console.WriteLine("----- Execute Delegate samples -----");

            // Use the new operator to create a delegate object
            var supermanDelegate = new CallAHeroByName(FuncSamples.FuncMethod);
            var superman = supermanDelegate("Superman");
            Console.WriteLine("Delegate: " + superman);

            // Use a (static) method to create a delegate object
            CallAHeroByName supergirlDelegate = FuncSamples.FuncMethod;
            var supergirl = supergirlDelegate("Supergirl");
            Console.WriteLine("Delegate: " + supergirl);

            // Use an old-school anonymous method
            // (that forwards to another method for simplicity and reuse)
            CallAHeroByName spidermanDelegate = delegate (string name) { return FuncSamples.FuncMethod(name); };
            var spiderman = spidermanDelegate("Spiderman");
            Console.WriteLine("Delegate: " + spiderman);

            // Use a well-known inline lambda
            // (that forwards to another method for simplicity and reuse)
            CallAHeroByName blackWidowDelegate = (name) => FuncSamples.FuncMethod(name);
            var blackWidow = spidermanDelegate("Black Widow");
            Console.WriteLine("Delegate: " + blackWidow);

            // Use a static method as parameter
            // Also works with previous examples
            MethodWithDelegateParam(FuncSamples.FuncMethod);

            Console.WriteLine("----- Finish Delegate samples -----");
        }

        // Does not work
        // public void MethodWithDelegateParam(delegate Hero DelegateTyp(string name))
        // {
        // }

        public void MethodWithDelegateParam(CallAHeroByName heroByNameDelegate)
        {
            var hero = heroByNameDelegate("Batman");
            Console.WriteLine("Delegate: " + hero);
        }
    }
}
