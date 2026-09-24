import { Navigate, Route, Routes } from 'react-router-dom'
import ApplicationLayout from './components/layout/ApplicationLayout'
import AuthenticatedRoute from './components/auth/AuthenticatedRoute'
import { AuthProvider } from './context/AuthContext'
import CartDetailsPage from './pages/CartDetailsPage'
import CartsPage from './pages/CartsPage'
import CategoriesPage from './pages/CategoriesPage'
import LoginPage from './pages/LoginPage'
import MealsPage from './pages/MealsPage'
import NotFoundPage from './pages/NotFoundPage'
import RegisterPage from './pages/RegisterPage'

const App = () => (
	<AuthProvider>
		<Routes>
			<Route element={<ApplicationLayout />}>
				<Route path="/login" element={<LoginPage />} />
				<Route path="/register" element={<RegisterPage />} />
				<Route element={<AuthenticatedRoute />}>
					<Route index element={<Navigate to="/meals" replace />} />
					<Route path="/meals" element={<MealsPage />} />
					<Route path="/categories" element={<CategoriesPage />} />
					<Route path="/cart" element={<CartsPage />} />
					<Route path="/carts" element={<Navigate to="/cart" replace />} />
					<Route path="/carts/:cartId" element={<CartDetailsPage />} />
				</Route>
				<Route path="*" element={<NotFoundPage />} />
			</Route>
		</Routes>
	</AuthProvider>
)

export default App
