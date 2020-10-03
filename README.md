# MovieDBApp


○ what architecture have you used and why?
  ○ I used a type of MVP, for the simple app idea using managers as Presentor to get data from url and save db of favorites and updating the View (fragments/items in recyclerview) and Movie objects as Model.
○ what was the most difficult part of the assignment?
  ○ The unit testing part is something I am not familiar with.
○ describe the main components, layers, architecture
  ○ There are 2 activities - one is a splash screen and another is the main screen. We have three fragments, one is a basic list activity, and another one is a MovieListPage (I created the base because I thought that I will extand another fragment fragment but after seeing that they look the same but the FAB and toggle to like movie.
  ○ We have 4 managers - 1) for network requests. 2) for Image loading. 3) for database. 4) general utils manager.
  ○ Movie is the model for a movie that coresponds with room entity. MovieDao is the room Dao. MovieDatabase is the room database of course.(List converter is an object te help retrive arraylist of objects)
  ○ MoviesListResponseObject is a response object to the main movies requests.
○ if there is any known bugs?
  ○ pressing quickly on items opens twice the fragment. without network the app will be stuck in the splash screen.
○ what would you change in the project?
  ○ At first I would add a paging machanisem(I added a function to increment the page number in the request.
  ○ I would add a mechanism for saving results so it can be shown when there is no network and update list once list is ready to show).
  ○ I would change the UI so when entering a details page we can slide in a viewpager manner to continue looking at other movie details.
