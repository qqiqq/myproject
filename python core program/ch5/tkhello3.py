import Tkinter

top = Tkinter.Tk()

hello = Tkinter.Label(top,text='Hello world!')
hello.pack()
quit = Tkinter.Button(top,text='Quit',command=top.quit,
                      bg='red',fg='white')
quit.pack(fill=Tkinter.X,expand=0.5)
Tkinter.mainloop()
