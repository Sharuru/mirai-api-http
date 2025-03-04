/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.api.http.context.session.manager

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.api.http.context.MahContextHolder
import net.mamoe.mirai.api.http.context.session.SampleAuthedSession
import net.mamoe.mirai.api.http.context.session.AuthedSession
import net.mamoe.mirai.api.http.context.session.Session
import net.mamoe.mirai.api.http.context.session.TempSession
import kotlin.coroutines.EmptyCoroutineContext

class DefaultSessionManager(override val verifyKey: String) : SessionManager {
    private val sessionMap: MutableMap<String, Session> = mutableMapOf()

    override fun createTempSession(): TempSession =
        TempSession(generateSessionKey(), EmptyCoroutineContext).also { newTempSession ->
            sessionMap[newTempSession.key] = newTempSession
            //设置180000ms后检测并回收
            newTempSession.launch {
                delay(180000)
                sessionMap[newTempSession.key]?.run {
                    if (this is TempSession) {
                        closeSession(newTempSession.key)
                    }
                }
            }
        }

    override fun authSession(bot: Bot, tempSessionKey: String) =
        authSession(tempSessionKey, SampleAuthedSession(bot, tempSessionKey, EmptyCoroutineContext))

    override fun authSession(bot: Bot, tempSession: TempSession) = authSession(bot, tempSession.key)

    override fun authSession(tempSession: TempSession, authedSession: AuthedSession): AuthedSession =
        authSession(tempSession.key, authedSession)

    override fun authSession(tempSessionKey: String, authedSession: AuthedSession): AuthedSession {
        closeSession(tempSessionKey)
        set(tempSessionKey, authedSession)

        /**
         * TODO: 从设计上解决循环依赖问题，目前 [MahContextHolder] 组合 [SessionManager]
         * TODO: 但 [SessionManager] 依赖了 [MahContextHolder]
         * TODO: bad design
         */
        MahContextHolder.listen(authedSession.bot, authedSession.key)

        return authedSession
    }

    override operator fun get(key: String) = sessionMap[key]

    override operator fun set(key: String, session: Session) = sessionMap.set(key, session)

    override fun closeSession(key: String) {
        sessionMap[key]?.close()
        sessionMap.remove(key)
    }

    override fun closeSession(session: Session) {
        closeSession(session.key)
    }

    override fun close() = sessionMap.forEach { (_, session) -> session.close() }

    override fun authedSessions(): List<AuthedSession> =
        sessionMap.filterValues { it is AuthedSession }.map { it.value as AuthedSession }
}
