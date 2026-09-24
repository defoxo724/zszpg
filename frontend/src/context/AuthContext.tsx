import { createContext, useContext, useEffect, useState, type ReactNode } from 'react'
import { UserApi } from '../api/UserApi'
import type { User } from '../types'

interface AuthContextValue {
	user: User | null
	loading: boolean
	signIn: (login: string, password: string) => Promise<void>
	signOut: () => Promise<void>
}
const AuthContext = createContext<AuthContextValue | null>(null)

export interface AuthProviderProps {
	children: ReactNode
}

export const AuthProvider = (props: AuthProviderProps) => {
	const [user, setUser] = useState<User | null>(null)
	const [loading, setLoading] = useState(true)

	useEffect(() => {
		const restoreSession = async () => {
			try {
				const currentUser = await UserApi.getSession()
				setUser(currentUser)
			} catch {
				setUser(null)
			} finally {
				setLoading(false)
			}
		}

		restoreSession()
	}, [])

	const signIn = async (login: string, password: string) => {
		const loggedInUser = await UserApi.login(login, password)
		setUser(loggedInUser)
	}

	const signOut = async () => {
		await UserApi.logout()
		setUser(null)
	}

	return (
		<AuthContext.Provider value={{ user, loading, signIn, signOut }}>
			{props.children}
		</AuthContext.Provider>
	)
}

export const useAuth = () => {
	const context = useContext(AuthContext)

	if (!context) {
		throw new Error('useAuth musi być używany wewnątrz AuthProvider.')
	}

	return context
}
