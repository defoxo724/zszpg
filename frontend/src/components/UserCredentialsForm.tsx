import { useState, type FormEvent } from 'react'

interface UserCredentialsFormProps {
	buttonLabel: string
	onSubmit: (login: string, password: string) => Promise<void>
}

const UserCredentialsForm = ({ buttonLabel, onSubmit }: UserCredentialsFormProps) => {
	const [login, setLogin] = useState('')
	const [password, setPassword] = useState('')
	const [submitting, setSubmitting] = useState(false)

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault()
		setSubmitting(true)
		try {
			await onSubmit(login, password)
		} finally {
			setSubmitting(false)
		}
	}

	return (
		<form onSubmit={handleSubmit}>
			<label className="form-label" htmlFor="login">
				Login
			</label>
			<input
				id="login"
				className="form-control mb-3"
				value={login}
				onChange={(event) => setLogin(event.target.value)}
				required
			/>
			<label className="form-label" htmlFor="password">
				Hasło
			</label>
			<input
				id="password"
				type="password"
				className="form-control mb-3"
				value={password}
				onChange={(event) => setPassword(event.target.value)}
				required
			/>
			<button className="btn btn-primary w-100" disabled={submitting}>
				{submitting ? 'Proszę czekać...' : buttonLabel}
			</button>
		</form>
	)
}
export default UserCredentialsForm
