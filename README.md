# HSoftwareChallenge

========
Challenge Details:
Instructions: Solve the challenge below using the programming language of your choice.  Use this opportunity to demonstrate your ideas about what makes good software.  Please provide your solution in a public source code repository of your choice.
Purpose: Clean nested repeated fields from a JSON document.
Details: A field is repeated if the name of the field exists at a higher level of nesting.  A field is not repeated if the field name occurs in a sibling object tree.
In this example, the "wholesaler" field should be removed from all the objects in the "contacts" array, since it occurs in the outermost object.

Before:
{
  "wholesaler":"US Foods",
  "delivered":"2015-06-19T05:15:00-0500",
  "contacts": [
    {
      "wholesaler":"US Foods",
      "name":"John Lederer"
    },
    {
      "wholesaler":"Sysco",
      "name":"Bill Delaney"
    }
  ]
}

After:
{
  "wholesaler":"US Foods",
  "delivered":"2015-06-19T05:15:00-0500",
  "contacts": [
    {
      "name":"John Lederer"
    },
    {
      "name":"Bill Delaney"
    }
  ]
}
