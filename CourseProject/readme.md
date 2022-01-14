Aplicatie pentru gestionarea cursurilor


Principala entitate din aplicație este entitatea „Course”, care are mai multe caracteristici: titlul,
cursului, descrierea, data când acesta a fost creat, dificultate,
Un curs face parte dintr-o anumită categorie, deci putem lega cursul de entitatea „Category”
printr-o relație 1 – M. Cursul este format din mai multe capitole, deci există o relație M-1 cu
entitatea „Chapter”. Un capitol poate sa contina una sau mai multe "Lesson".
Fiecare Course contine o evaluare, la rândul ei, entitatea „Evaluation” este legată de entitatea
„Questions” printr-o relație 1 – M.



4 main features cu 10 business requirements


1. Feature -> Sa existe un sistem mai complex de cautare
   1.1 Sa poata fi listate cursurile dintr-o anumita categorie
   1.2 Sa poata fi cautate lectiile dintr-un curs dupa cuvinte din titlu

2. Feature -> Sa existe mai multe moduri de afisare si sortare ale cursurilor pentru diferite
   moduri de afisare in client
   2.1 Sa poata fi listate cursurile cu anumite campuri din ele (specificate) -
   2.2 Sa poata fi listate cursurile ordonate dupa diferite criterii (in acelasi API cu cel de mai sus)


3. Feature -> Sa existe evaluari pentru cursuri
   3.1 Sa poata fi create evaluari cu intrebari pentru cursuri
   3.2 Sa poata fi actualizate intrebarile din evaluari
   3.3 Sa poata fi vizualizate toate intrebarile pentru un curs

4. Feature -> Sa poata fi stocate cursuri cu lectii
   4.1 Sa poata fi adaugate cursuri
   4.2 Cursurile ar trebui structurate in capitole
   4.3 Capitolele ar trebui structurate in lectii 
