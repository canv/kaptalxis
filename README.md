# REST API service designed to manage the list of all books in the database.

## Data in the DB table
- ```id``` - the identifier of the book in the database
- ```title``` - title of the book
- ```description``` - a short description of what the book is about
- ```isbn``` - ISBN books
- ```printYear``` - in what year was the book published
- ```readAlready``` - has the book been read
- ```image``` - book cover

## Declared Features:
- create a book
- update the book (replace the edition)
- mark the book as read
- get a list of books with pagination
- get info on the selected book
- find a book by phrase

Replacing the edition - all fields are edited, except for the author, and the ```readAlready``` mark is reset
