To run the server use
java Server
To run the client user
java GuiClient

Once connected you can send

- messages
- private messages (with /w)
- latex messages (/latex)

Demo latex messae: /latex x^2=4
Will open a new window where it will render that formula.

It has lots of bugs. Moast annoying:
- if you close the latex window after the first message the nexte messages will just open an empty window
- some formulas throw erros (eg.: /latex x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a} )
- some elements are not rendered corred (eg.: /latex \\frac{a}{b} )
ETC.

