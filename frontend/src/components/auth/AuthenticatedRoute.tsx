import Spinner from 'react-bootstrap/Spinner'
import { Navigate, Outlet, useLocation } from 'react-router-dom'
import type { UserType } from '../../types'
import { useAuth } from '../../context/AuthContext'

interface AuthenticatedRouteProps {
	requiredUserType?: UserType
}

const AuthenticatedRoute = (props: AuthenticatedRouteProps) => {
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
		return <Navigate to="/login" replace state={{ from: location.pathname }} />
	}

	if (props.requiredUserType && user.type !== props.requiredUserType) {
		return <Navigate to={user.type === 'ADMIN' ? '/meals' : '/order'} replace />
	}

	return <Outlet />
}
export default AuthenticatedRoute
