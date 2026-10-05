import { Route, Routes } from 'react-router-dom'
import ApplicationLayout from './components/layout/ApplicationLayout'
import AuthenticatedRoute from './components/auth/AuthenticatedRoute'
import HomeRedirect from './components/auth/HomeRedirect'
import { AuthProvider } from './context/AuthContext'
import CartDetailsPage from './pages/CartDetailsPage'
import CartsPage from './pages/CartsPage'
import CategoriesPage from './pages/CategoriesPage'
import LoginPage from './pages/LoginPage'
import MealsPage from './pages/MealsPage'
import NotFoundPage from './pages/NotFoundPage'
import RegisterPage from './pages/RegisterPage'
import OrdererPage from './pages/OrdererPage'
import ApplicationSettingsPage from './pages/ApplicationSettingsPage'

const App = () => (
	<AuthProvider>
		<Routes>
			<Route index element={<HomeRedirect />} />
			<Route element={<ApplicationLayout />}>
				<Route path="/login" element={<LoginPage />} />
				<Route path="/register" element={<RegisterPage />} />
			</Route>
			<Route element={<AuthenticatedRoute requiredUserType="ADMIN" />}>
				<Route element={<ApplicationLayout />}>
					<Route path="/meals" element={<MealsPage />} />
					<Route path="/categories" element={<CategoriesPage />} />
					<Route path="/settings" element={<ApplicationSettingsPage />} />
					<Route path="/carts" element={<CartsPage />} />
					<Route path="/carts/:cartId" element={<CartDetailsPage />} />
				</Route>
			</Route>
			<Route element={<AuthenticatedRoute requiredUserType="ORDERER" />}>
				<Route path="/order" element={<OrdererPage />} />
			</Route>
			<Route path="*" element={<NotFoundPage />} />
		</Routes>
	</AuthProvider>
)

export default App
