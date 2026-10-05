import Spinner from 'react-bootstrap/Spinner'
import { Navigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

const HomeRedirect = () => {
	const { user, loading } = useAuth()

	if (loading) {
		return (
			<div className="text-center py-5">
				<Spinner className="me-2" size="sm" />
				Ładowanie sesji...
			</div>
		)
	}

	if (!user) return <Navigate to="/login" replace />
	return <Navigate to={user.type === 'ADMIN' ? '/meals' : '/order'} replace />
}

export default HomeRedirect
