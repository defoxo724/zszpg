import type { ReactNode } from 'react'
import Card from 'react-bootstrap/Card'
import { Link } from 'react-router-dom'

interface AuthenticationCardProps {
	title: string
	footerText: string
	footerLinkText: string
	footerLinkTo: string
	children: ReactNode
}

const AuthenticationCard = (props: AuthenticationCardProps) => (
	<Card className="auth-card">
		<Card.Body>
			<Card.Title as="h1" className="h3 mb-3">
				{props.title}
			</Card.Title>
			{props.children}
			<p className="mt-3 mb-0">
				{props.footerText} <Link to={props.footerLinkTo}>{props.footerLinkText}</Link>
			</p>
		</Card.Body>
	</Card>
)

export default AuthenticationCard
