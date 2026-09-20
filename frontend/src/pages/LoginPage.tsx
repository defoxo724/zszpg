import { useState } from 'react'
import { Link, Navigate, useLocation, useNavigate } from 'react-router-dom'
import UserCredentialsForm from '../components/UserCredentialsForm'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'

interface LoginLocationState {
	from?: string
}

const LoginPage = () => {
	const { user, signIn } = useAuth()
	const navigate = useNavigate()
	const location = useLocation()
	const [error, setError] = useState<string | null>(null)

	if (user) {
		return <Navigate to="/meals" replace />
	}

	const handleLogin = async (login: string, password: string) => {
		try {
			await signIn(login, password)

			const previousLocation = (location.state as LoginLocationState | null)?.from
			navigate(previousLocation ?? '/meals')
		} catch {
			setError('Nieprawidłowy login lub hasło.')
		}
	}

	return (
		<div className="card auth-card">
			<div className="card-body">
				<h1 className="h3 mb-3">Logowanie</h1>
				<StatusMessage message={error} />
				<UserCredentialsForm buttonLabel="Zaloguj" onSubmit={handleLogin} />
				<p className="mt-3 mb-0">
					Nie masz konta? <Link to="/register">Zarejestruj się</Link>
				</p>
			</div>
		</div>
	)
}
export default LoginPage
