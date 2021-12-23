const Registration = { template: '<registration></registration>' }
const Login = { template: '<login></login>' }
const UserProfile = { template: '<userProfile></userProfile>' }
const UsersOverview = {template: '<usersOverview></usersOverview>' }
const NewRestaurant = {template: '<newRestaurant></newRestaurant>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/registration', component: Registration },
		{ path: '/login', component: Login },
	    { path: '/userProfile', component: UserProfile },
		{ path: '/usersOverview', component: UsersOverview },
		{ path: '/newRestaurant', component: NewRestaurant }

	]
});

var app = new Vue({
	router,
	el: '#ordering_system'
});