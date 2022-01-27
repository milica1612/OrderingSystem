const Registration = { template: '<registration></registration>' }
const Login = { template: '<login></login>' }
const UserProfile = { template: '<userProfile></userProfile>' }
const UsersOverview = {template: '<usersOverview></usersOverview>' }
const NewRestaurant = {template: '<newRestaurant></newRestaurant>' }
const EmployeeRegistration = {template: '<employeeRegistration></employeeRegistration>' }
const CreateOrder = {template: '<createOrder></createOrder>' }
const ManagerRegistration = {template: '<managerRegistration></managerRegistration>'}
const RestaurantPage = {template: '<restaurantPage></restaurantPage>'}
const NewItem = {template: '<newItem></newItem>' }
const ChangePassword = {template: '<changePassword></changePassword>' }
const CartOverview = {template: '<cartOverview></cartOverview>' }
const OrdersOverview = {template: '<ordersOverview></ordersOverview>' }
const ManagerOrderOverview = {template: '<managerOrderOverview></managerOrderOverview>' }
const ManagerDeliveryRequests = {template: '<managerDeliveryRequests></managerDeliveryRequests>' }
const DelivererOrderOverview = {template: '<delivererOrderOverview></delivererOrderOverview>' }
const OrdersWithoutDeliverer = {template: '<ordersWithoutDeliverer></ordersWithoutDeliverer>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/registration', component: Registration },
		{ path: '/login', component: Login },
	    { path: '/userProfile', component: UserProfile },
		{ path: '/usersOverview', component: UsersOverview },
		{ path: '/newRestaurant', component: NewRestaurant },
		{ path: '/employeeRegistration', component: EmployeeRegistration },
		{ path: '/createOrder', component: CreateOrder },
		{ path: '/managerRegistration', component: ManagerRegistration },
		{ path: '/restaurantPage', component: RestaurantPage },
		{ path: '/newItem', component: NewItem },
		{ path: '/changePassword', component: ChangePassword },
		{ path: '/cartOverview', component: CartOverview },
		{ path: '/ordersOverview', component: OrdersOverview },
		{ path: '/managerOrderOverview', component: ManagerOrderOverview },
		{ path: '/managerDeliveryRequests', component: ManagerDeliveryRequests },
		{ path: '/delivererOrderOverview', component: DelivererOrderOverview },
		{ path: '/ordersWithoutDeliverer', component: OrdersWithoutDeliverer }

	]
});

var app = new Vue({
	router,
	el: '#ordering_system'
});