from Tkinter import *

def resize(ev=None):
    hello.config(font='Helvetica -%d bold' % scale.get())

top = Tk()
top.geometry('250x150')

hello = Label(top,text='Hello world!',font='Helvetica -12 bold')
hello.pack(fill=Y,expand=1)

scale = Scale(top,from_=10,to=40,orient=HORIZONTAL,command=resize)
scale.set(12)
scale.pack(fill=X,expand=1)

quit = Button(top,text='Quit',command=top.quit,
              activebackground='red',activeforeground='white')
quit.pack()

mainloop()
