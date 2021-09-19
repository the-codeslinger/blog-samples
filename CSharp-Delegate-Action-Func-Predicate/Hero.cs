namespace NamedPredicates
{
    public class Hero
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public Hero(int id, string name) => (Id, Name) = (id, name);

        override public string ToString() => Name;
    }
}
