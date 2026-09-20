import { Navigate, Outlet, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const AuthenticatedRoute = () => {
	const { user, loading } = useAuth()
	const location = useLocation()

	if (loading) {
		return (
			<div className="text-center py-5">
				<Spinner className="me-2" size="sm" />
				Ładowanie sesji...
			</div>
		)
	}

	if (!user) {
		return (
			<Navigate
				to="/login"
				replace
				state={{
					from: location.pathname,
				}}
			/>
		)
	}

	return <Outlet />
}
export default AuthenticatedRoute
import Spinner from 'react-bootstrap/Spinner'
